import { describe, vi } from "vitest";

const mockFetch = (response: Response) => {
  vi.stubGlobal("fetch", vi.fn().mockResolvedValueOnce(response));
}

describe("Game Page", () => {

});
