package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.models.ShoeListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ListingController {

    @Autowired
    private ListingRepository listingRepository;

    @GetMapping("/listing")
    public String showListingForm(Model model) {
        model.addAttribute("listing", new ShoeListing());
        return "listing-form";
    }

    @PostMapping("/listing")
    public String createListing(@ModelAttribute("listing") ShoeListing shoeListing,
                                @RequestParam("photoFile") MultipartFile photoFile) {
        try {
            if (!photoFile.isEmpty()) {
                // Get the bytes of the photo file
                byte[] photoBytes = photoFile.getBytes();

                // Set the photo bytes to the listing
                shoeListing.setPhoto(photoBytes);
            }

            // Save the listing to the database
            listingRepository.save(shoeListing);
        } catch (Exception e) {
            // Handle any exceptions, e.g., return to the form with an error message
            return "redirect:/listing?error=upload";
        }

        // Redirect to a success page
        return "redirect:/success";
    }
}
