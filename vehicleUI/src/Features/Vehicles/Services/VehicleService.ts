import type { Vehicle } from "../Types/Vehicle";

const API_URL = "http://localhost:8080/api/vehicles";

async function handleResponse<T>(res: Response): Promise<T> {
    if (!res.ok) {
        throw new Error(`HTTP error: ${res.status}`);
    }
    return res.json();
}

export const getVehicles = async (): Promise<Vehicle[]> => {
    const res = await fetch(API_URL);
    return handleResponse<Vehicle[]>(res);
};

export const postVehicle = async (
    vehicle: Omit<Vehicle, "id">
): Promise<Vehicle> => {
    const res = await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(vehicle),
    });

    return handleResponse<Vehicle>(res);
};

export const putVehicle = async (
    id: number,
    vehicle: Vehicle
): Promise<void> => {
    const res = await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(vehicle),
    });

    if (!res.ok) throw new Error(`HTTP error: ${res.status}`);
};

export const deleteVehicle = async (id: number): Promise<void> => {
    const res = await fetch(`${API_URL}/${id}`, {
        method: "DELETE",
    });

    if (!res.ok) throw new Error(`HTTP error: ${res.status}`);
};