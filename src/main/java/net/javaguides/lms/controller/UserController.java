package net.javaguides.lms.controller;

import net.javaguides.lms.entity.User;
import net.javaguides.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PatchMapping("/{id}/blacklist")
    public ResponseEntity<User> blacklistUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.blacklistUser(id));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PatchMapping("/{id}/unblacklist")
    public ResponseEntity<User> unblacklistUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.unblacklistUser(id));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}