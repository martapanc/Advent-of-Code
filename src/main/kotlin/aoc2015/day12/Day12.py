
import json


def read_json():
    with open('input.json') as f:
        data = json.load(f)
    print(data)


if __name__ == '__main__':
    read_json()


