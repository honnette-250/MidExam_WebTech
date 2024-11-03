package rw.management.OnlineManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import rw.management.OnlineManagementSystem.model.Course;
import rw.management.OnlineManagementSystem.repository.CourseRepository;

import java.util.List;
import java.util.Optional;
import java.io.File;
import java.io.IOException;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course courseDetails) {
        Course course = courseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Course not found"));
        
        course.setName(courseDetails.getName());
        course.setDescription(courseDetails.getDescription());
        course.setDuration(courseDetails.getDuration());
        course.setPicture(courseDetails.getPicture()); // Add this line

        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public List<Course> searchCourses(String name) {
        return courseRepository.findByNameContaining(name);
    }

    // Change the access modifier to public
    public String savePicture(MultipartFile file) throws IOException { 
        String fileName = file.getOriginalFilename();
        String filePath = "path/to/your/pictures/directory/" + fileName; // Change to your desired directory
        File destinationFile = new File(filePath);
        
        file.transferTo(destinationFile);
        return "/api/pictures/" + fileName; // Change this to match your URL structure
    }
}
