import pytest
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as ec


@pytest.fixture(scope="module")
def driver():
    driver = webdriver.Chrome()
    driver.get("http://localhost:3000")
    driver.implicitly_wait(10)
    yield driver
    driver.quit()

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
