from flask import Flask, request, jsonify
import numpy as np
from sklearn.ensemble import IsolationForest

app = Flask(__name__)

# Training data
data = np.array([
    [100], [200], [300], [400], [500],
    [600], [700], [800], [900], [1000],
    [1500], [2000], [2500], [3000],
    [5000], [7000], [9000]
])

model = IsolationForest(contamination=0.05)
model.fit(data)

@app.route("/predict", methods=["POST"])
def predict():
    amount = request.json.get("amount")

    print("🔥 Received amount:", amount)

    # Rule-based override
    if amount > 50000:
        return jsonify({"status": "ANOMALY", "score": -1.0})

    prediction = model.predict([[amount]])
    score = model.decision_function([[amount]])[0]

    result = "ANOMALY" if prediction[0] == -1 else "NORMAL"

    return jsonify({
        "status": result,
        "score": float(score)
    })

if __name__ == "__main__":
    app.run(port=5000)