import { useEffect, useState } from "react";
import { useAuth } from "../../Auth/Hooks/useAuth";
import { hasPermission } from "../../Auth/Utils/permissions";
import { UserService } from "../Services/UserService";
import type {
  UserResponseDTO,
  UserCreateDTO,
  UserUpdateDTO,
} from "../Types/User";
import UserTable from "../Components/UserTable";
import UserForm from "../Components/UserForm";
import CommonModal from "../../../Shared/Components/CommonModal";
import Button from "react-bootstrap/Button";
import useToast from "../../../Shared/Hooks/useToast";
import ToastNotification from "../../../Shared/Components/ToastNotification";

const UsersPage = () => {
  const { user } = useAuth();
  const [users, setUsers] = useState<UserResponseDTO[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingUser, setEditingUser] = useState<UserResponseDTO | null>(null);
  const [showConfirm, setShowConfirm] = useState(false);
  const [userToDelete, setUserToDelete] = useState<UserResponseDTO | null>(
    null,
  );
  const [isSubmitting, setIsSubmitting] = useState(false);
  const { message, show, triggerToast, closeToast } = useToast();

  const canCreate = hasPermission(user, "USER_CREATE");
  const canUpdate = hasPermission(user, "USER_UPDATE");
  const canDelete = hasPermission(user, "USER_DELETE");

  const fetchUsers = async () => {
    try {
      setIsLoading(true);
      const data = await UserService.getAll();
      setUsers(data);
    } catch (error) {
      triggerToast(
        error instanceof Error ? error.message : "Failed to load users",
      );
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const handleAdd = () => {
    setEditingUser(null);
    setShowModal(true);
  };

  const handleEdit = (user: UserResponseDTO) => {
    setEditingUser(user);
    setShowModal(true);
  };

  const handleDeleteClick = (user: UserResponseDTO) => {
    setUserToDelete(user);
    setShowConfirm(true);
  };

  const confirmDelete = async () => {
    if (!userToDelete) return;
    try {
      await UserService.delete(userToDelete.id);
      setUsers((prev) => prev.filter((u) => u.id !== userToDelete.id));
      triggerToast("User deleted successfully");
      setUserToDelete(null);
      setShowConfirm(false);
    } catch (error) {
      triggerToast(error instanceof Error ? error.message : "Delete failed");
    }
  };

  const handleFormSave = async (data: UserCreateDTO | UserUpdateDTO) => {
    setIsSubmitting(true);
    try {
      if (editingUser) {
        const updated = await UserService.update(
          editingUser.id,
          data as UserUpdateDTO,
        );
        setUsers((prev) =>
          prev.map((u) => (u.id === updated.id ? updated : u)),
        );
        triggerToast("User updated successfully");
      } else {
        const created = await UserService.create(data as UserCreateDTO);
        setUsers((prev) => [...prev, created]);
        triggerToast("User created successfully");
      }
      setShowModal(false);
      setEditingUser(null);
    } catch (error) {
      triggerToast(error instanceof Error ? error.message : "Save failed");
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h1>Users</h1>
      </div>

      {canCreate && (
        <Button className="mb-2" variant="primary" onClick={handleAdd}>
          Add User
        </Button>
      )}

      {isLoading ? (
        <p>Loading users...</p>
      ) : (
        <UserTable
          users={users}
          onEdit={canUpdate ? handleEdit : undefined}
          onDelete={canDelete ? handleDeleteClick : undefined}
        />
      )}

      {/* User Form Modal */}
      <CommonModal
        show={showModal}
        title={editingUser ? "Edit User" : "Add User"}
        onClose={() => setShowModal(false)}
      >
        <UserForm
          user={editingUser}
          onSave={handleFormSave}
          onCancel={() => setShowModal(false)}
          isSubmitting={isSubmitting}
        />
      </CommonModal>

      {/* Delete Confirmation Modal */}
      <CommonModal
        show={showConfirm}
        title="Delete User"
        onClose={() => setShowConfirm(false)}
      >
        <p>
          Are you sure you want to delete{" "}
          <strong>{userToDelete?.username}</strong>?
        </p>
        <div className="d-flex justify-content-end gap-2">
          <Button variant="secondary" onClick={() => setShowConfirm(false)}>
            Cancel
          </Button>
          <Button variant="danger" onClick={confirmDelete}>
            Delete
          </Button>
        </div>
      </CommonModal>

      <ToastNotification message={message} show={show} onClose={closeToast} />
    </div>
  );
};

export default UsersPage;
