
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Courses | Telusko</title>

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

        .container {
            width: 84%;
            max-width: 1100px;
            margin: 0 auto;
        }

        .courses-section {
            padding: 70px 0;
            min-height: 78vh;
        }

        .section-header {
            text-align: center;
            margin-bottom: 45px;
            animation: fadeIn 0.7s ease;
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
            margin-bottom: 20px;
        }

        h1 {
            font-size: clamp(32px, 4vw, 45px);
            color: #0f172a;
            margin-bottom: 15px;
        }

        .highlight {
            color: #4f46e5;
        }

        .subtitle {
            color: #64748b;
            font-size: 16px;
            line-height: 1.7;
        }

        .course-grid {
            display: grid;
            grid-template-columns: repeat(2, minmax(0, 1fr));
            gap: 22px;
        }

        .course-card {
            display: flex;
            align-items: center;
            gap: 20px;
            padding: 28px;
            background: white;
            border: 1px solid #e2e8f0;
            border-radius: 18px;
            box-shadow: 0 8px 25px rgba(15, 23, 42, 0.03);
            transition: all 0.3s ease;
            animation: fadeIn 0.7s ease;
        }

        .course-card:hover {
            transform: translateY(-6px);
            border-color: #a5b4fc;
            box-shadow: 0 15px 35px rgba(79, 70, 229, 0.10);
        }

        .course-icon {
            width: 60px;
            height: 60px;
            flex-shrink: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 16px;
            background: #eef2ff;
            font-size: 28px;
        }

        .course-card h3 {
            font-size: 20px;
            color: #1e293b;
            margin-bottom: 8px;
        }

        .course-card p {
            font-size: 14px;
            color: #64748b;
            line-height: 1.6;
        }

        .back-btn {
            display: inline-block;
            margin-top: 35px;
            padding: 13px 23px;
            border-radius: 10px;
            background: #4f46e5;
            color: white;
            text-decoration: none;
            font-weight: 700;
            transition: all 0.3s ease;
        }

        .back-btn:hover {
            background: #3730a3;
            transform: translateY(-3px);
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
                transform: translateY(15px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        @media (max-width: 700px) {
            .container {
                width: 90%;
            }

            .course-grid {
                grid-template-columns: 1fr;
            }

            .course-card {
                padding: 22px;
            }

            .navbar {
                padding: 18px 5%;
            }

            .logo {
                font-size: 22px;
            }

            .courses-section {
                padding: 45px 0;
            }
        }
    </style>
</head>

<body>

    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/home"
           class="logo">Telusko.</a>

        <a href="${pageContext.request.contextPath}/home"
           class="nav-link">← Home</a>
    </nav>

    <main class="courses-section">
        <div class="container">

            <div class="section-header">
                <span class="badge">OUR LEARNING PATHS</span>

                <h1>Explore Our <span class="highlight">Courses</span></h1>

                <p class="subtitle">
                    Choose a technology and start your learning journey.
                </p>
            </div>

            <div class="course-grid">

                <c:forEach var="book" items="${books}">
                    <div class="course-card">

                        <div class="course-icon">
                            <c:choose>
                                <c:when test="${book == 'Java'}">☕</c:when>
                                <c:when test="${book == 'DevOps'}">⚙️</c:when>
                                <c:when test="${book == 'AWS'}">☁️</c:when>
                                <c:otherwise>🤖</c:otherwise>
                            </c:choose>
                        </div>

                        <div>
                            <h3><c:out value="${book}"/></h3>
                            <p>
                                Explore new concepts, improve your skills,
                                and build practical knowledge.
                            </p>
                        </div>

                    </div>
                </c:forEach>

            </div>

            <a href="${pageContext.request.contextPath}/home"
               class="back-btn">← Back to Home</a>

        </div>
    </main>

    <footer class="footer">
        © 2026 Telusko. Keep learning, keep growing.
    </footer>

</body>
</html>
