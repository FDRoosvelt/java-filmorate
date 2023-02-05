# java-filmorate
Работа по созданию соцсети с тематикой фильмов

Схема
![image](https://github.com/FDRoosvelt/java-filmorate/blob/main/QuickDBD-filmorate.png)

##########################################################################

Получение списка фильмов с жанрами, рейтингами и кол-вом лайков

SELECT f.film_Id,
	f.name,
	f.description,
	f.duration,
	gl.name AS genre,
	mrl.name AS rating,
	f.release_Date,
	COUNT(l.film_Id) AS likes
FROM Films AS f
INNER JOIN Film_MPA_Ratingt AS fmr ON f.film_Id = fmr.film_Id
INNER JOIN MPA_Rating_List AS mrl ON f.rating_Id = mrl.rating_Id
INNER JOIN Film_Genre AS fg ON f.film_Id = fg.film_Id
INNER JOIN Genre_List AS gl ON fg.genre_Id = gl.genre_Id
INNER JOIN Likes AS l ON f.film_Id = l.film_Id
GROUP BY f.film_Id,
	f.name,
	f.description,
	f.duration,
	genre,
	rating,
	f.release_Date
ORDER BY f.film_Id

##########################################################################

Получение списка лайков

SELECT f.name,
	u.name AS user
FROM Films AS f.
INNER JOIN Likes AS l ON f.film_Id = l.film_Id
INNER JOIN Users AS u ON l.user_Id = u.user_Id
GROUP BY f.name,
	user
ORDER BY f.name 

##########################################################################

Получение списка юзеров с кол-вом лайков и кол-вом лайкнутых фильмов

SELECT u.user_Id,
	u.name,
	u.login,
	u.email,
	u.birthday,
	COUNT(fl.name) AS friends_count,
	COUNT(l.film_Id) AS likes
FROM Users AS u
INNER JOIN Friend_List AS fl ON u.user_Id = fl.user_Id
INNER JOIN Likes AS l ON u.films_Id = l.user_Id
WHERE fl.status_id = 1
GROUP BY u.user_Id,
	u.name,
	u.login,
	u.email,
	u.birthday
ORDER BY u.user_Id

##########################################################################

Получение списка друзей юзера

SELECT us.name,
	us.email
FROM Users AS u
INNER JOIN Friend_List AS fl ON u.user_Id = fl.user_Id
INNER JOIN Users AS us ON fl.friend_Id = us.user_Id
WHERE u.user_Id = 1
GROUP BY us.name,
	us.email
ORDER BY us.name
