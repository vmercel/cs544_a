package edu.miu.cs.cs544.mercel.jpa.monitoring.vitals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vitals")
public class VitalsController {

    @Autowired
    private VitalsService vitalsService;

    // Get all vitals by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Vitals>> getVitalsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(vitalsService.getVitalsByUserId(userId));
    }

    // Create a new vitals entry
    @PostMapping
    public ResponseEntity<Vitals> createVitals(@RequestBody Vitals vitals) {
        return ResponseEntity.ok(vitalsService.createVitals(vitals));
    }

    // Update vitals
    @PutMapping("/{id}")
    public ResponseEntity<Vitals> updateVitals(@PathVariable Long id, @RequestBody Vitals updatedVitals) {
        return ResponseEntity.ok(vitalsService.updateVitals(id, updatedVitals));
    }

    // Delete vitals
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVitals(@PathVariable Long id) {
        vitalsService.deleteVitals(id);
        return ResponseEntity.noContent().build();
    }
}

