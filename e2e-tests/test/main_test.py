import pytest
from selenium import webdriver
from selenium.webdriver.common.by import By


@pytest.fixture(scope="module")
def driver():
    driver = webdriver.Chrome()
    driver.get("http://localhost:3000")
    driver.implicitly_wait(10)
    yield driver
    driver.quit()

def test_title(driver):
    assert driver.title == "이터널리턴 밸런스게임"
