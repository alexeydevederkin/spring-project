package com.asd.employee.controller;

import com.asd.employee.exception.DepartmentNotFoundException;
import com.asd.employee.exception.PositionNotFoundException;
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
    public Position getOnePosition(@PathVariable("id") Integer id) {
        return positionRepo.findById(id).orElseThrow(() -> new PositionNotFoundException(id));
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
        }).orElseThrow(() -> new PositionNotFoundException(id));
    }

    @DeleteMapping("{id}")
    public void deletePosition(@PathVariable("id") Integer id) {
        positionRepo.deleteById(id);
    }
}
