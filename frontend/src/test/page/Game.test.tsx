import { describe, expect, vi } from "vitest";
import { act, cleanup, render, screen, waitFor } from "@testing-library/react";
import Game from "../../page/Game.tsx";
import userEvent from "@testing-library/user-event";

const mockGetQuestionAPI = vi.fn();
const mockSelectChoiceAPI = vi.fn();
const mockGetChoiceResultAPI = vi.fn();

describe("Game Page", () => {
  beforeEach(() => {
  });

  afterEach(() => {
    cleanup();
    mockGetQuestionAPI.mockReset();
    mockSelectChoiceAPI.mockReset();
    mockGetChoiceResultAPI.mockReset();
  });


  it("기본 상태 렌더링", async () => {
    mockGetQuestionAPI.mockResolvedValueOnce({
      ok: true,
      json: async () => ({
          id: 2,
          questionText: "질문2",
          choiceA: "선택지 A2",
          choiceB: "선택지 B2",
          createdDate: "2024-12-11T04:17:45.752+00:00",
          updatedDate: "2024-12-11T04:17:45.752+00:00",
          choiceACount: 0,
          choiceBCount: 0
        }
      ),
    });

    render(<Game
      callGetQuestionAPI={ mockGetQuestionAPI }
      callSelectChoiceAPI={ mockSelectChoiceAPI }
      callGetChoiceResultAPI={ mockGetChoiceResultAPI }
    />);

    expect(await screen.findByText(/질문/)).toBeInTheDocument();
    expect(screen.getByText(/선택지 A2/)).toBeInTheDocument();
    expect(screen.getByText(/선택지 B2/)).toBeInTheDocument();
    expect(mockGetQuestionAPI).toBeCalledTimes(1);
  });

  it("선택지 A를 선택했을 때", async () => {
  });

  it("선택지 B를 선택했을 때", async () => {

  });

  it("다음 질문을 가져오는 함수를 호출했을 때", async () => {

  });

  it("백엔드 API 호출 시 에러가 발생했을 때 에러 모달 노출", async () => {

  });
});
