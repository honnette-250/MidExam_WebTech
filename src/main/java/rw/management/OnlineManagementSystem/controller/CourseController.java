package rw.management.OnlineManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // Import MultipartFile

import rw.management.OnlineManagementSystem.model.Course;
import rw.management.OnlineManagementSystem.service.CourseService;

import java.util.List;
import java.io.IOException; // Import IOException

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Get all courses
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // Get course by ID
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return ResponseEntity.ok(course);
    }

    // Add a new course with file upload
    @PostMapping(consumes = "multipart/form-data") // Specify the consumes attribute
    public ResponseEntity<Course> addCourse(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("duration") int duration,
            @RequestParam(value = "picture", required = false) MultipartFile picture) throws IOException { // Declare IOException

        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setDuration(duration);

        // Handle the file upload if a picture is provided
        if (picture != null && !picture.isEmpty()) {
            String picturePath = courseService.savePicture(picture); // Call to save the picture
            course.setPicture(picturePath);
        }

        Course savedCourse = courseService.addCourse(course);
        return ResponseEntity.ok(savedCourse);
    }

    // Update course
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course courseDetails) {
        Course updatedCourse = courseService.updateCourse(id, courseDetails);
        return ResponseEntity.ok(updatedCourse);
    }

    // Delete course
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    // Search courses by name
    @GetMapping("/search")
    public List<Course> searchCourses(@RequestParam String name) {
        return courseService.searchCourses(name);
    }
}
