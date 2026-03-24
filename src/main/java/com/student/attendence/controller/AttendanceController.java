package com.student.attendence.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.student.attendence.model.Attendance;
import com.student.attendence.repository.AttendanceRepository;

@CrossOrigin(origins = "*")   // ✅ CORRECT PLACE
@RestController
public class AttendanceController {

    @Autowired
    AttendanceRepository repo;

    // ✅ ADD
    @PostMapping("/add")
    public Attendance addAttendence(@RequestBody Attendance attendance) {
        System.out.println("Saving: " + attendance.getName());
        Attendance saved = repo.save(attendance);
        System.out.println("Saved ID: " + saved.getId());
        return saved;
    }

    // ✅ GET
    @GetMapping("/list")
    public List<Attendance> getAll() {
        return repo.findAll();
    }

    // ✅ DELETE
    @DeleteMapping("/remove/{id}")
    public String deleteAttandence(@PathVariable Long id) {
        repo.deleteById(id);
        return "record deleted successfully";
    }

    // ✅ PUT (Full Update)
    @PutMapping("/update/{id}")
    public Attendance updateAttendance(@PathVariable Long id, @RequestBody Attendance newData) {
        return repo.findById(id).map(att -> {
            att.setName(newData.getName());
            att.setDate(newData.getDate());
            return repo.save(att);
        }).orElse(null);
    }

    // ✅ PATCH (Partial Update)
    @PatchMapping("/update/{id}")
    public Attendance updatePartial(@PathVariable Long id, @RequestBody Attendance updatedData) {

        Attendance existing = repo.findById(id).orElse(null);

        if (existing != null) {

            if (updatedData.getName() != null) {
                existing.setName(updatedData.getName());
            }

            if (updatedData.getDate() != null) {
                existing.setDate(updatedData.getDate());
            }

            return repo.save(existing);
        }

        return null;
    }
}