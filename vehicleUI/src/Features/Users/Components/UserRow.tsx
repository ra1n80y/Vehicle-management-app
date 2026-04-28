import Button from "react-bootstrap/Button";
import type { UserResponseDTO } from "../Types/User";

type Props = {
  user: UserResponseDTO;
  onEdit?: (user: UserResponseDTO) => void;
  onDelete?: (user: UserResponseDTO) => void;
};

const UserRow = ({ user, onEdit, onDelete }: Props) => {
  return (
    <tr>
      <td>{user.id}</td>
      <td>{user.username}</td>
      <td>{user.active ? "Active" : "Disabled"}</td>
      <td>{user.roles.join(", ")}</td>
      <td>
        {onEdit && (
          <Button
            variant="warning"
            size="sm"
            className="me-1"
            onClick={() => onEdit(user)}
          >
            Edit
          </Button>
        )}
        {onDelete && (
          <Button variant="danger" size="sm" onClick={() => onDelete(user)}>
            Delete
          </Button>
        )}
      </td>
    </tr>
  );
};

export default UserRow;
