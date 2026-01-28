package com.PawsAndHomes.Backend.Dto.Pet;


import lombok.Data;

@Data
public class PetRequestDTO {
    private String name;
    private String breed;
    private Integer age;
    private String gender;
    private String size;
    private Long ngoId;

    
    public PetRequestDTO() {}

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
    
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    
    public Long getNgoId() { return ngoId; }
    public void setNgoId(Long ngoId) { this.ngoId = ngoId; }
}