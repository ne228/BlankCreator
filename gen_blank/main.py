from flask import Flask, request, jsonify, send_file
from blank_creator import create_blank_docx, create_blank_pdf
import os
app = Flask(__name__)

@app.route('/create_blank_docx', methods=['POST'])
def get_blank_docx():
    # Получаем JSON из запроса
    data = request.get_json(force=True)
    print("request", request.data)

    # Проверка, является ли полученные данные списком
    if not isinstance(data, list):
        return jsonify({"error": "Expected a list of dictionaries"}), 400
    
      

    file_path = create_blank_docx(data)
    file_name = os.path.basename(file_path)
    return send_file(file_path, as_attachment=True,  download_name=file_name)

@app.route('/create_blank_pdf', methods=['POST'])
def get_blank_pdf():
    # Получаем JSON из запроса
    data = request.json

    # Проверка, является ли полученные данные списком
    if not isinstance(data, list):
        return jsonify({"error": "Expected a list of dictionaries"}), 400

    file_path = create_blank_pdf(data)
    file_name = os.path.basename(file_path)
    return send_file(file_path, as_attachment=True,  download_name=file_name)

from dotenv import load_dotenv

# Загружаем переменные окружения из .env файла
load_dotenv('../.env')

# Получаем значение порта из переменной окружения
port = int(os.getenv('PY_PORT', 5005))  # 5005 - значение по умолчанию, если переменная не найдена


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=port)
