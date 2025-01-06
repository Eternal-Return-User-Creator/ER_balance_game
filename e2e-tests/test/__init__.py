from selenium import webdriver

def driver_factory(browser: str):
    if browser == "chrome":
        return webdriver.Chrome()
    elif browser == "firefox":
        return webdriver.Firefox()
    elif browser == "safari":
        return webdriver.Safari()
    else:
        raise ValueError(f"Unsupported browser: {browser}")