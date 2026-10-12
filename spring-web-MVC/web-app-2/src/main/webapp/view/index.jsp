<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Spring Boot Journey</title>

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 24px;
            font-family: "Segoe UI", Arial, sans-serif;
            color: #e2e8f0;
            background:
                radial-gradient(circle at 15% 20%, #164e634d, transparent 40%),
                radial-gradient(circle at 85% 80%, #14532d66, transparent 40%),
                #0b1220;
        }

        .card {
            width: 100%;
            max-width: 760px;
            padding: 52px;
            border: 1px solid #ffffff1a;
            border-radius: 28px;
            background: #111c2ee6;
            box-shadow: 0 24px 80px #00000040;
        }

        .brand {
            display: flex;
            align-items: center;
            gap: 12px;
            margin-bottom: 44px;
            font-size: 18px;
            font-weight: 700;
        }

        .logo {
            width: 44px;
            height: 44px;
            display: grid;
            place-items: center;
            border-radius: 14px;
            background: #86efac;
            color: #052e16;
            font-size: 18px;
        }

        .badge {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 8px 13px;
            border: 1px solid #86efac33;
            border-radius: 100px;
            color: #86efac;
            background: #86efac0d;
            font-size: 12px;
            font-weight: 600;
        }

        .dot {
            width: 7px;
            height: 7px;
            border-radius: 50%;
            background: #86efac;
        }

        h1 {
            margin: 24px 0 16px;
            font-size: clamp(34px, 6vw, 54px);
            line-height: 1.12;
            letter-spacing: -2px;
        }

        h1 span {
            color: #86efac;
        }

        .description {
            max-width: 520px;
            margin: 0;
            color: #94a3b8;
            font-size: 16px;
            line-height: 1.8;
        }

        .message-box {
            margin: 30px 0;
            padding: 20px;
            border: 1px solid #ffffff14;
            border-left: 3px solid #86efac;
            border-radius: 12px;
            background: #0b1220;
        }

        .message-label {
            margin-bottom: 10px;
            color: #86efac;
            font-size: 11px;
            font-weight: 700;
            letter-spacing: 1.5px;
            text-transform: uppercase;
        }

        .message-box p {
            margin: 0;
            font-size: 17px;
            line-height: 1.6;
            overflow-wrap: anywhere;
        }

        .actions {
            display: flex;
            flex-wrap: wrap;
            gap: 12px;
        }

        .button {
            display: inline-block;
            padding: 13px 20px;
            border: 1px solid transparent;
            border-radius: 10px;
            font-size: 14px;
            font-weight: 600;
            text-decoration: none;
            transition: transform 0.2s, background 0.2s;
        }

        .button:hover {
            transform: translateY(-2px);
        }

        .button:focus-visible {
            outline: 3px solid #38bdf8;
            outline-offset: 4px;
        }

        .primary {
            color: #052e16;
            background: #86efac;
        }

        .primary:hover {
            background: #bbf7d0;
        }

        .secondary {
            color: #e2e8f0;
            border-color: #ffffff26;
            background: transparent;
        }

        .secondary:hover {
            background: #ffffff0d;
        }

        .footer {
            margin-top: 40px;
            padding-top: 20px;
            border-top: 1px solid #ffffff14;
            color: #94a3b8;
            font-size: 12px;
            line-height: 1.6;
        }

        @media (max-width: 600px) {
            .card {
                padding: 30px 24px;
            }

            .brand {
                margin-bottom: 32px;
            }

            h1 {
                letter-spacing: -1px;
            }

            .button {
                width: 100%;
                text-align: center;
            }
        }
    </style>
</head>

<body>
    <main class="card">
        <div class="brand">
            <div class="logo" aria-hidden="true">&lt;/&gt;</div>
            <span>Java Journey</span>
        </div>

        <div class="badge">
            <span class="dot" aria-hidden="true"></span>
            My first Spring Boot web app
        </div>

        <h1>
            Small beginnings.<br>
            <span>Big possibilities.</span>
        </h1>

        <p class="description">
            Welcome to my Java development journey.
            Exploring Spring Boot, building web applications,
            and learning something new every day.
        </p>

        <section class="message-box" aria-label="Controller message">
            <div class="message-label">Message from the controller</div>
            <p>${message}</p>
        </section>

        <nav class="actions" aria-label="Demo endpoints">
            <a class="button primary"
               href="${pageContext.request.contextPath}/telusko1">
                Open Telusko 1 &rarr;
            </a>

            <a class="button secondary"
               href="${pageContext.request.contextPath}/telusko2">
                Open Telusko 2 &rarr;
            </a>
        </nav>

        <footer class="footer">
            Built with Java &bull; Spring Boot &bull; JSP
        </footer>
    </main>
</body>
</html>