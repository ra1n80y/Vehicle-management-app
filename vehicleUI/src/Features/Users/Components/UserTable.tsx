import Table from "react-bootstrap/Table";
import UserRow from "./UserRow";
import type { UserResponseDTO } from "../Types/User";

type Props = {
  users: UserResponseDTO[];
  onEdit?: (user: UserResponseDTO) => void;
  onDelete?: (user: UserResponseDTO) => void;
};

const UserTable = ({ users, onEdit, onDelete }: Props) => {
  return (
    <Table striped bordered hover responsive>
      <thead className="table-dark">
        <tr>
          <th>ID</th>
          <th>Username</th>
          <th>Status</th>
          <th>Roles</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {users.map((user) => (
          <UserRow
            key={user.id}
            user={user}
            onEdit={onEdit}
            onDelete={onDelete}
          />
        ))}
      </tbody>
    </Table>
  );
};

export default UserTable;
