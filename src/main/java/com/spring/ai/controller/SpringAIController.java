package com.spring.ai.controller;


import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.stabilityai.api.StabilityAiImageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SpringAIController {

	@Autowired
	ImageModel openaiImageModel;

	// 1. Endpoint to show the landing page
	@GetMapping("/")
	public String showLandingPage() {
		return "index"; // This returns the "index.html" Thymeleaf template
	}

	// 2. Endpoint to handle the form submission and generate the image
	@PostMapping("/generateImage")
	public String generateImage(@RequestParam("prompt") String prompt, Model model) {
		// Call the AI model to generate the image
		ImageResponse response = openaiImageModel.call(new ImagePrompt(prompt, StabilityAiImageOptions.builder()
				.withStylePreset("cinematic").withN(1).withHeight(1024).withWidth(1024).build()));

		// Get the Base64 string of the generated image
		String base64Image = response.getResult().getOutput().getB64Json();

		// Add the image and prompt to the model to display in the frontend
		model.addAttribute("generatedImage", base64Image);
		model.addAttribute("prompt", prompt);

		return "imageResult"; // This returns the "imageResult.html" Thymeleaf template
	}
}
