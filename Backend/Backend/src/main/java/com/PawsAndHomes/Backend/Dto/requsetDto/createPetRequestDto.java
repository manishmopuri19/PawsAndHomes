package com.PawsAndHomes.Backend.Dto.requsetDto;

import jakarta.validation.constraints.NotBlank;

public class createPetRequestDto {
	
	 	@NotBlank(message = "Name is required")
	    private String name;
	 	@NotBlank(message = "Name is required")
	    private Integer age;
	 	@NotBlank(message = "Name is required")
	    private String breed;
	    
	    
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		public String getBreed() {
			return breed;
		}
		public void setBreed(String breed) {
			this.breed = breed;
		}

}
