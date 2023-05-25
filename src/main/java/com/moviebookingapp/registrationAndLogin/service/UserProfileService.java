package com.moviebookingapp.registrationAndLogin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviebookingapp.registrationAndLogin.dto.ErrorResponse;
import com.moviebookingapp.registrationAndLogin.dto.SuccessResponse;
import com.moviebookingapp.registrationAndLogin.dto.UserProfileDTO;
import com.moviebookingapp.registrationAndLogin.model.User;
import com.moviebookingapp.registrationAndLogin.repository.UserProfileRepository;

@Service
public class UserProfileService {
	
	
	@Autowired
	UserProfileRepository userProfileRepository;
	
	public String CreateUserProfile(UserProfileDTO userProfileDTO) {
		
		String response = null;

		List<ErrorResponse> listErrorResponse = validateUserProfile(userProfileDTO);
		if (!CollectionUtils.isEmpty(listErrorResponse)) {
			response = prepareErrorResponse(listErrorResponse);
			return response;
		}

		User userProfile = transformIntoUserEntity(userProfileDTO);
		userProfileRepository.save(userProfile);

		return prepareSuccessResponse();
	}
	
	private String prepareErrorResponse(List<ErrorResponse> listErrorResponse) {
		ObjectMapper objectMapper = new ObjectMapper();
		String response = null;
		try {
			response = objectMapper.writeValueAsString(listErrorResponse);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}
	
	private User transformIntoUserEntity(UserProfileDTO userProfileDTO) {

		User userProfile = new User();
	
		userProfile.setLoginId(userProfileDTO.getLoginId());
		userProfile.setFirstName(userProfileDTO.getFirstName());
		userProfile.setLastName(userProfileDTO.getLastName());
		userProfile.setUsername(userProfile.getUsername());
		userProfile.setEmail(userProfileDTO.getEmail());
		userProfile.setPassword(userProfileDTO.getPassword());
		userProfile.setConfirmPassword(userProfileDTO.getConfirmPassword());
		userProfile.setContactNo(userProfileDTO.getContactNo());

		return userProfile;
	}

	private String prepareSuccessResponse() {
		SuccessResponse successResponse = new SuccessResponse();
		successResponse.setMessage("Your registration has been successfully completed");
		ObjectMapper objectMapper = new ObjectMapper();
		String response = null;
		try {
			response = objectMapper.writeValueAsString(successResponse);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	private List<ErrorResponse> validateUserProfile(UserProfileDTO userProfileDTO) {

		List<ErrorResponse> listErrorResponse = new ArrayList<>();

		if (StringUtils.hasText(userProfileDTO.getLoginId())) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("LoginId");
			errorResponse.setDetail("Login Id is mandatory or missing");
			listErrorResponse.add(errorResponse);
		}
		if (StringUtils.hasText(userProfileDTO.getUsername())) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("Username");
			errorResponse.setDetail("Username is mandatory or missing");
			listErrorResponse.add(errorResponse);
		}
		if (StringUtils.hasText(userProfileDTO.getFirstName())) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("Firstname");
			errorResponse.setDetail("Firstname is mandatory or missing");
			listErrorResponse.add(errorResponse);
		}
		if (StringUtils.hasText(userProfileDTO.getLastName())) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("Lastname");
			errorResponse.setDetail("Lastname is mandatory or missing");
			listErrorResponse.add(errorResponse);
		}
		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{6,10}$";

		Pattern p = Pattern.compile(regex);

		Matcher m = p.matcher(userProfileDTO.getPassword());

		if (!m.matches()) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("password");
			errorResponse.setDetail(
					"Password should contain atleast 6 characters, uppercase, lowercase, special characters and numbers");

			listErrorResponse.add(errorResponse);
		}
		Matcher n = m;
		
		if(!n.equals(m)) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("confirmPassword");
			errorResponse.setDetail(
					"Confirm Password should match with Password");

			listErrorResponse.add(errorResponse);
		}

		String regex1 = "^(.+)@(.+).(.+)$";

		Pattern p1 = Pattern.compile(regex1);

		Matcher m1 = p1.matcher(userProfileDTO.getEmail());

		if (!m1.matches()) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("Email");
			errorResponse.setDetail("Enter a Vaild Email Address");

			listErrorResponse.add(errorResponse);
		}

		Pattern p3 = Pattern.compile("[7-9][0-9]{9}");

		Matcher m3 = p3.matcher(userProfileDTO.getContactNo());

		if (!m3.matches()) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("contactNumber");
			errorResponse.setDetail("Contact number should have 10 digits");

			listErrorResponse.add(errorResponse);
		}

		
		
		Optional<User> userOpt= userProfileRepository.findByLoginId(userProfileDTO.getLoginId());
		if(userOpt.isPresent()) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("LoginId");
			errorResponse.setDetail("LoginId is already present");
			
			listErrorResponse.add(errorResponse);
		}

		return listErrorResponse;
	}

}
