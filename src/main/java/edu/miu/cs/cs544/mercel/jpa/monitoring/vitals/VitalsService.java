package edu.miu.cs.cs544.mercel.jpa.monitoring.vitals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VitalsService {

    @Autowired
    private VitalsRepository vitalsRepository;

    public List<Vitals> getVitalsByUserId(Long userId) {
        return vitalsRepository.findByUserId(userId);
    }

    public Vitals createVitals(Vitals vitals) {
        return vitalsRepository.save(vitals);
    }

    public Vitals updateVitals(Long id, Vitals updatedVitals) {
        Vitals existingVitals = vitalsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vitals not found with id: " + id));
        existingVitals.setHeartRate(updatedVitals.getHeartRate());
        existingVitals.setCaloriesBurned(updatedVitals.getCaloriesBurned());
        existingVitals.setSteps(updatedVitals.getSteps());
        existingVitals.setRecordDate(updatedVitals.getRecordDate());
        return vitalsRepository.save(existingVitals);
    }

    public void deleteVitals(Long id) {
        vitalsRepository.deleteById(id);
    }
}

