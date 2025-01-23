import pytest
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as ec


def check_initial_texts(question, choices, ratios):
    assert question.text != ''
    assert len(choices) == 2
    assert choices[0].text != ''
    assert choices[1].text != ''
    assert len(ratios) == 2
    assert ratios[0].text == ''
    assert ratios[1].text == ''

@pytest.fixture(scope="function")
def driver(driver):
    driver.get("http://localhost:3000/game")
    driver.implicitly_wait(10)
    yield driver

def test_게임_페이지_표시(driver):
    question = WebDriverWait(driver, 10).until(
        ec.visibility_of_element_located((By.CSS_SELECTOR, 'p.in-game.question'))
    )
    choices = driver.find_elements(By.CLASS_NAME, 'choice-text')
    ratios = driver.find_elements(By.CLASS_NAME, 'choice-ratio')
    next_button = driver.find_element(By.CLASS_NAME, 'next-button')

    check_initial_texts(question, choices, ratios)
    assert next_button.is_displayed()

@pytest.mark.parametrize('choice_index', [0, 1])
def test_선택지_클릭_시_결과_표시(driver, choice_index):
    driver.get('http://localhost:3000/game')
    choices = driver.find_elements(By.CLASS_NAME, 'choice-text')
    ratios = driver.find_elements(By.CLASS_NAME, 'choice-ratio')

    choices[choice_index].click()

    WebDriverWait(driver, 10).until(
        ec.visibility_of_element_located((By.CLASS_NAME, 'choice-ratio'))
    )

    assert ratios[0].text != ''
    assert ratios[1].text != ''

def test_다음_질문_버튼_클릭_시_다음_질문_표시(driver):
    driver.get('http://localhost:3000/game')

    next_button = driver.find_element(By.CLASS_NAME, 'next-button')
    next_button.click()

    WebDriverWait(driver, 10).until(
        ec.visibility_of_element_located((By.CSS_SELECTOR, 'p.in-game.question'))
    )

    question = driver.find_element(By.CSS_SELECTOR, 'p.in-game.question')
    choices = driver.find_elements(By.CLASS_NAME, 'choice-text')
    ratios = driver.find_elements(By.CLASS_NAME, 'choice-ratio')

    check_initial_texts(question, choices, ratios)
