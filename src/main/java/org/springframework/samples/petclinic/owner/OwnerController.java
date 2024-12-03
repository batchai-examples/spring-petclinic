/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class OwnerController {

    private final OwnerRepository owners;

    public OwnerController(OwnerRepository owners) {
        this.owners = owners;
    }

    @GetMapping("/owners/find")
    public String initFindForm() {
        return "owners/findOwners";
    }

    @GetMapping("/owners")
    public String processFindForm(@RequestParam(defaultValue = "1") int page, Owner owner, BindingResult result,
                                  Model model) {
        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        Page<Owner> ownersResults = findPaginatedForOwnersLastName(page, owner.getLastName());
        if (ownersResults.isEmpty()) {
            // no owners found
            result.rejectValue("lastName", "notFound", "No owners found with the specified last name.");
            return "owners/findOwners";
        }

        if (ownersResults.getTotalElements() == 1) {
            // 1 owner found
            owner = ownersResults.iterator().next();
            return "redirect:/owners/" + owner.getId();
        }

        // multiple owners found
        return addPaginationModel(page, model, ownersResults);
    }

    private String addPaginationModel(int page, Model model, Page<Owner> paginated) {
        List<Owner> listOwners = paginated.getContent();
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", paginated.getTotalPages());
        model.addAttribute("totalItems", paginated.getTotalElements());
        model.addAttribute("listOwners", listOwners);
        return "owners/ownersList";
    }

    private Page<Owner> findPaginatedForOwnersLastName(int page, String lastname) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return owners.findByLastName(lastname, pageable);
    }

    @GetMapping("/owners/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
        Optional<Owner> ownerOptional = this.owners.findById(ownerId);
        if (ownerOptional.isPresent()) {
            Owner owner = ownerOptional.get();
            model.addAttribute(owner);
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            // Handle the case where the owner is not found
            model.addAttribute("error", "Owner not found");
            return "owners/ownerDetails";
        }
    }

    @PostMapping("/owners/{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result, @PathVariable("ownerId") int ownerId,
                                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "There was an error in updating the owner.");
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        }

        owner.setId(ownerId);
        this.owners.save(owner);
        redirectAttributes.addFlashAttribute("message", "Owner Values Updated");
        return "redirect:/owners/{ownerId}";
    }

    /**
     * Custom handler for displaying an owner.
     * @param ownerId the ID of the owner to display
     * @return a ModelMap with the model attributes for the view
     */
    @GetMapping("/owners/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") int ownerId) {
        Optional<Owner> ownerOptional = this.owners.findById(ownerId);
        if (ownerOptional.isPresent()) {
            Owner owner = ownerOptional.get();
            ModelAndView mav = new ModelAndView("owners/ownerDetails");
            mav.addObject(owner);
            return mav;
        } else {
            // Handle the case where the owner is not found
            ModelAndView mav = new ModelAndView("owners/ownerDetails");
            mav.addObject("error", "Owner not found");
            return mav;
        }
    }

}
