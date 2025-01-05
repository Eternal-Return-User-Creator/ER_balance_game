from selenium import webdriver

driver = webdriver.Chrome()

driver.get("http://localhost:3000")

title = driver.title

def test_title():
    assert title == "이터널리턴 밸런스게임"

driver.quit()