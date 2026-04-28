import { useState, useEffect } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import type {
  UserResponseDTO,
  UserCreateDTO,
  UserUpdateDTO,
} from "../Types/User";
import { RoleService } from "../Services/RoleService";
import type { RoleResponseDTO } from "../Types/Role";

type Props = {
  user: UserResponseDTO | null;
  onSave: (data: UserCreateDTO | UserUpdateDTO) => Promise<void>;
  onCancel: () => void;
  isSubmitting?: boolean;
};

const UserForm = ({ user, onSave, onCancel, isSubmitting = false }: Props) => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    active: true,
    roles: [] as string[],
  });
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [availableRoles, setAvailableRoles] = useState<RoleResponseDTO[]>([]);
  const [loadingRoles, setLoadingRoles] = useState(true);

  useEffect(() => {
    const fetchRoles = async () => {
      try {
        const roles = await RoleService.getAll();
        setAvailableRoles(roles);
      } catch (error) {
        console.error("Failed to load roles", error);
      } finally {
        setLoadingRoles(false);
      }
    };
    fetchRoles();
  }, []);

  useEffect(() => {
    if (user) {
      setFormData({
        username: user.username,
        password: "",
        active: user.active,
        roles: user.roles || [],
      });
    } else {
      setFormData({
        username: "",
        password: "",
        active: true,
        roles: [],
      });
    }
  }, [user]);

  const handleChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement
    >,
  ) => {
    const { name, value, type } = e.target;
    const checked = (e.target as HTMLInputElement).checked;
    setFormData((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
    setErrors((prev) => ({ ...prev, [name]: "" }));
  };

  const handleRoleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedOptions = Array.from(e.target.selectedOptions).map(
      (opt) => opt.value,
    );
    setFormData((prev) => ({ ...prev, roles: selectedOptions }));
  };

  const validate = (): boolean => {
    const newErrors: Record<string, string> = {};
    if (!formData.username.trim()) newErrors.username = "Username is required";
    if (!user && !formData.password)
      newErrors.password = "Password is required";
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validate()) return;

    const payload: UserCreateDTO | UserUpdateDTO = user
      ? {
          username: formData.username,
          active: formData.active,
          roles: formData.roles,
        }
      : {
          username: formData.username,
          password: formData.password,
          active: formData.active,
          roles: formData.roles,
        };

    await onSave(payload);
  };

  return (
    <Form onSubmit={handleSubmit}>
      <Form.Group className="mb-3" controlId="username">
        <Form.Label>Username</Form.Label>
        <Form.Control
          type="text"
          name="username"
          value={formData.username}
          onChange={handleChange}
          isInvalid={!!errors.username}
          disabled={isSubmitting}
        />
        <Form.Control.Feedback type="invalid">
          {errors.username}
        </Form.Control.Feedback>
      </Form.Group>

      {!user && (
        <Form.Group className="mb-3" controlId="password">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            isInvalid={!!errors.password}
            disabled={isSubmitting}
          />
          <Form.Control.Feedback type="invalid">
            {errors.password}
          </Form.Control.Feedback>
        </Form.Group>
      )}

      <Form.Group className="mb-3" controlId="active">
        <Form.Check
          type="checkbox"
          label="Active"
          name="active"
          checked={formData.active}
          onChange={handleChange}
          disabled={isSubmitting}
        />
      </Form.Group>

      <Form.Group className="mb-3" controlId="roles">
        <Form.Label>Roles</Form.Label>
        {loadingRoles ? (
          <p>Loading roles...</p>
        ) : (
          <Form.Select
            multiple
            value={formData.roles}
            onChange={handleRoleChange}
            disabled={isSubmitting}
            size="sm"
            style={{ minHeight: "120px" }}
          >
            {availableRoles.map((role) => (
              <option key={role.id} value={role.name}>
                {role.name}
              </option>
            ))}
          </Form.Select>
        )}
        <Form.Text muted>Hold Ctrl/Cmd to select multiple roles.</Form.Text>
      </Form.Group>

      <div className="d-flex justify-content-end gap-2">
        <Button variant="secondary" onClick={onCancel} disabled={isSubmitting}>
          Cancel
        </Button>
        <Button variant="primary" type="submit" disabled={isSubmitting}>
          {isSubmitting ? "Saving..." : user ? "Update" : "Create"}
        </Button>
      </div>
    </Form>
  );
};

export default UserForm;
