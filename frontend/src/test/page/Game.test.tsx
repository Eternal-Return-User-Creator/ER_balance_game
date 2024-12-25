import { describe, vi } from "vitest";

const mockFetch = (response: Response) => {
  vi.stubGlobal("fetch", vi.fn().mockResolvedValueOnce(response));
}

describe("Game Page", () => {
  it("기본 상태 렌더링", async () => {

  });

  it ("선택지 A를 선택했을 때", async () => {

  });

  it ("선택지 B를 선택했을 때", async () => {

  });

  it ("다음 질문을 가져오는 함수를 호출했을 때", async () => {

  });

  it ("백엔드 API 호출 시 에러가 발생했을 때 에러 모달 노출", async () => {

  });
});
