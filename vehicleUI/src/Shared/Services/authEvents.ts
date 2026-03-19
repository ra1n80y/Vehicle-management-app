type UnauthorizedListener = () => void;

let unauthorizedListener: UnauthorizedListener | null = null;

export const registerUnauthorizedListener = (
 listener: UnauthorizedListener,
): void => {
  unauthorizedListener = listener;
};

export const clearUnauthorizedListener = (): void => {
  unauthorizedListener = null;
};

export const notifyUnauthorized = (): void => {
  unauthorizedListener?.();
};