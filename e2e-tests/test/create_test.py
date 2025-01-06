import pytest
from selenium.webdriver.common.by import By


@pytest.fixture(scope='module')
def driver(driver):
    driver.get('http://localhost:3000/create')
    yield driver

def test_질문_생성_페이지_표시(driver):
    title = driver.find_element(By.CLASS_NAME, 'title')
    description = driver.find_element(By.CLASS_NAME, 'description')
    create_button = driver.find_element(By.CSS_SELECTOR, 'button.create-question')
    reset_button = driver.find_element(By.CLASS_NAME, 'reset')

    assert title.text == '질문 만들기'
    assert description.text == '질문을 작성하시는 걸 도와드릴게요!'
    assert create_button.text == '만들기'
    assert reset_button.is_displayed() == False

