
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home | Telusko</title>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: "Segoe UI", Arial, sans-serif;
            min-height: 100vh;
            background: linear-gradient(135deg, #eef2ff, #f8fafc);
            color: #1e293b;
        }

        .navbar {
            background: white;
            padding: 22px 8%;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 4px 20px rgba(15, 23, 42, 0.06);
        }

        .logo {
            font-size: 27px;
            font-weight: 800;
            color: #4f46e5;
            text-decoration: none;
        }

        .nav-link {
            text-decoration: none;
            color: #475569;
            font-weight: 600;
            transition: color 0.3s;
        }

        .nav-link:hover {
            color: #4f46e5;
        }

        .hero {
            min-height: 78vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 40px 20px;
        }

        .hero-card {
            width: 100%;
            max-width: 800px;
            text-align: center;
            padding: 65px 40px;
            background: white;
            border-radius: 25px;
            box-shadow: 0 20px 60px rgba(79, 70, 229, 0.10);
            animation: fadeIn 0.8s ease;
        }

        .badge {
            display: inline-block;
            background: #eef2ff;
            color: #4f46e5;
            padding: 9px 18px;
            border-radius: 50px;
            font-size: 12px;
            font-weight: 800;
            letter-spacing: 1.5px;
            margin-bottom: 25px;
        }

        h1 {
            font-size: clamp(34px, 5vw, 55px);
            line-height: 1.2;
            color: #0f172a;
            margin-bottom: 22px;
        }

        .highlight {
            color: #4f46e5;
        }

        .description {
            max-width: 600px;
            margin: 0 auto 35px;
            font-size: 17px;
            line-height: 1.8;
            color: #64748b;
        }

        .btn {
            display: inline-block;
            background: #4f46e5;
            color: white;
            padding: 15px 28px;
            border-radius: 11px;
            text-decoration: none;
            font-weight: 700;
            transition: all 0.3s ease;
        }

        .btn:hover {
            background: #3730a3;
            transform: translateY(-3px);
            box-shadow: 0 10px 25px rgba(79, 70, 229, 0.25);
        }

        .footer {
            text-align: center;
            padding: 25px 15px;
            color: #94a3b8;
            font-size: 14px;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        @media (max-width: 600px) {
            .navbar {
                padding: 18px 5%;
            }

            .logo {
                font-size: 22px;
            }

            .hero-card {
                padding: 45px 22px;
            }

            .description {
                font-size: 15px;
            }
        }
    </style>
</head>

<body>

    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/home"
           class="logo">Telusko.</a>

        <a href="${pageContext.request.contextPath}/response"
           class="nav-link">Explore Courses →</a>
    </nav>

    <main class="hero">
        <div class="hero-card">

            <span class="badge">LEARN. BUILD. GROW.</span>

            <h1>
                Welcome to <span class="highlight">Telusko</span>
            </h1>

            <p class="description">
                Upgrade your skills with modern technology courses.
                Explore Java, DevOps, AWS and AI Engineering to build
                your future in technology.
            </p>

            <a href="${pageContext.request.contextPath}/response"
               class="btn">Explore Courses →</a>

        </div>
    </main>

    <footer class="footer">
        © 2026 Telusko. Learn something new every day.
    </footer>

</body>
</html>
