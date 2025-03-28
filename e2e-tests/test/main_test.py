import pytest
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as ec


@pytest.fixture(scope="function")
def driver(driver):
    driver.get("http://localhost:3000")
    yield driver

def test_타이틀_표시(driver):
    assert driver.title == "이터널리턴 밸런스게임"

def test_게임_시작_버튼_클릭_시_게임_페이지로_이동(driver):
    # 게임 시작 버튼의 애니메이션이 끝날 때까지 기다림(최대 10초)
    game_start_button = WebDriverWait(driver, 10).until(
        ec.visibility_of_element_located((By.ID, "game-start-button"))
    )
    assert game_start_button.text == "게임 시작"

    game_start_button.click()
    driver.implicitly_wait(10)
    assert driver.current_url == "http://localhost:3000/game"

def test_처음으로_클릭_시_메인_페이지로_이동(driver):
    back_to_main = driver.find_element(By.ID, "back-to-main-link")
    back_to_main.click()
    driver.implicitly_wait(10)
    assert driver.current_url == "http://localhost:3000/"

def test_게임_시작_클릭_시_게임_페이지로_이동(driver):
    game_start = driver.find_element(By.ID, "game-start-link")
    game_start.click()
    driver.implicitly_wait(10)
    assert driver.current_url == "http://localhost:3000/game"

def test_만들기_클릭_시_질문_생성_페이지로_이동(driver):
    create_question = driver.find_element(By.ID, "create-question-link")
    create_question.click()
    driver.implicitly_wait(10)
    assert driver.current_url == "http://localhost:3000/create"
