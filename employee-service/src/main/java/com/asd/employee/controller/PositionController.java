package com.asd.employee.controller;

import com.asd.employee.model.Position;
import com.asd.employee.repo.PositionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("position")
public class PositionController {
    private PositionRepo positionRepo;

    @Autowired
    public PositionController(PositionRepo positionRepo) {
        this.positionRepo = positionRepo;
    }

    @GetMapping
    public List<Position> getAllPositions() {
        return positionRepo.findAll();
    }

    @GetMapping("{id}")
    public Position getOnePosition(@PathVariable("id") Position position) {
        return position;
    }

    @PostMapping
    public Position createPosition(@RequestBody Position position) {
        return positionRepo.save(position);
    }

    @PutMapping("{id}")
    public Position updatePosition(@PathVariable("id") Integer id, @RequestBody Position newPosition) {
        return positionRepo.findById(id).map(position -> {
            position.setName(newPosition.getName());
            return positionRepo.save(position);
        }).orElseGet(() -> {
            newPosition.setId(id);
            return positionRepo.save(newPosition);
        });
    }

    @DeleteMapping("{id}")
    public void deletePosition(@PathVariable("id") Integer id) {
        positionRepo.deleteById(id);
    }
}
