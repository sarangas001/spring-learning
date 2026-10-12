
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Course Information</title>

    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: "Segoe UI", Arial, sans-serif;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
            background: linear-gradient(135deg, #e0e7ff, #f8fafc);
            color: #1e293b;
        }

        .card {
            width: 100%;
            max-width: 480px;
            background: white;
            padding: 35px;
            border-radius: 20px;
            box-shadow: 0 15px 45px rgba(30, 41, 59, 0.12);
            animation: appear 0.6s ease;
        }

        .badge {
            display: inline-block;
            background: #eef2ff;
            color: #4f46e5;
            padding: 8px 14px;
            border-radius: 30px;
            font-size: 12px;
            font-weight: bold;
            margin-bottom: 18px;
        }

        h1 {
            font-size: 30px;
            margin-bottom: 10px;
            color: #0f172a;
        }

        .subtitle {
            color: #64748b;
            margin-bottom: 28px;
            line-height: 1.6;
        }

        .detail {
            display: flex;
            justify-content: space-between;
            gap: 15px;
            padding: 17px 0;
            border-bottom: 1px solid #e2e8f0;
        }

        .detail:last-of-type {
            border-bottom: none;
        }

        .label {
            color: #64748b;
        }

        .value {
            font-weight: 700;
            text-align: right;
            overflow-wrap: anywhere;
        }

        .price {
            color: #4f46e5;
        }

        .btn {
            display: block;
            margin-top: 28px;
            padding: 14px;
            text-align: center;
            background: #4f46e5;
            color: white;
            text-decoration: none;
            border-radius: 10px;
            font-weight: 600;
            transition: 0.3s;
        }

        .btn:hover {
            background: #3730a3;
            transform: translateY(-2px);
        }

        @keyframes appear {
            from {
                opacity: 0;
                transform: translateY(15px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
    </style>
</head>

<body>

    <div class="card">
        <span class="badge">COURSE DETAILS</span>

        <h1>Course Information</h1>
        <p class="subtitle">
            Details of the selected technology course.
        </p>

        <div class="detail">
            <span class="label">Course ID</span>
            <span class="value">${cid}</span>
        </div>

        <div class="detail">
            <span class="label">Course Name</span>
            <span class="value">${cname}</span>
        </div>

        <div class="detail">
            <span class="label">Course Cost</span>
            <span class="value price">${cost}</span>
        </div>

        <a href="${pageContext.request.contextPath}/course-details"
           class="btn">View DevOps Course →</a>
    </div>

</body>
</html>
