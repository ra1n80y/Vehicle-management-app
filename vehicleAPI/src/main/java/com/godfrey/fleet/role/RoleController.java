package com.godfrey.fleet.role;

import com.godfrey.fleet.role.dto.RoleCreateDTO;
import com.godfrey.fleet.role.dto.RolePatchDTO;
import com.godfrey.fleet.role.dto.RoleResponseDTO;
import com.godfrey.fleet.role.dto.RoleUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> listRoles() {
        return ResponseEntity.ok(roleService.listRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> getRole(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRole(id));
    }

    @PostMapping
    public ResponseEntity<RoleResponseDTO> createRole(@Valid @RequestBody RoleCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleUpdateDTO dto) {
        return ResponseEntity.ok(roleService.updateRole(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> patchRole(
            @PathVariable Long id,
            @Valid @RequestBody RolePatchDTO dto) {
        return ResponseEntity.ok(roleService.patchRole(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}