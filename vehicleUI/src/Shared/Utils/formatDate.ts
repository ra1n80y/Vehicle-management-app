/**
 * Formats an ISO date string to a human‑readable locale string.
 * Returns the original string if parsing fails.
 */

export const formatTimestamp = (value: string): string => {
  const date = new Date(value);
  if (Number.isNaN(date.getTime())) return value;
  return date.toLocaleString();
};