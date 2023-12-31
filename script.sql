USE [master]
GO
/****** Object:  Database [BookStore]    Script Date: 11/11/2023 12:56:32 AM ******/
CREATE DATABASE [BookStore]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'BookStore', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\BookStore.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'BookStore_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\BookStore_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [BookStore] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [BookStore].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [BookStore] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [BookStore] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [BookStore] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [BookStore] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [BookStore] SET ARITHABORT OFF 
GO
ALTER DATABASE [BookStore] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [BookStore] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [BookStore] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [BookStore] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [BookStore] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [BookStore] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [BookStore] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [BookStore] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [BookStore] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [BookStore] SET  ENABLE_BROKER 
GO
ALTER DATABASE [BookStore] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [BookStore] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [BookStore] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [BookStore] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [BookStore] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [BookStore] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [BookStore] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [BookStore] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [BookStore] SET  MULTI_USER 
GO
ALTER DATABASE [BookStore] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [BookStore] SET DB_CHAINING OFF 
GO
ALTER DATABASE [BookStore] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [BookStore] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [BookStore] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [BookStore] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [BookStore] SET QUERY_STORE = ON
GO
ALTER DATABASE [BookStore] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [BookStore]
GO
/****** Object:  Table [dbo].[Books]    Script Date: 11/11/2023 12:56:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Books](
	[book_id] [int] IDENTITY(1,1) NOT NULL,
	[title] [varchar](255) NULL,
	[author] [varchar](255) NULL,
	[category_name] [varchar](255) NULL,
	[price] [decimal](10, 2) NULL,
	[description] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[book_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Cart]    Script Date: 11/11/2023 12:56:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Cart](
	[user_id] [int] NULL,
	[book_id] [int] NULL,
	[quantity] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Order_book]    Script Date: 11/11/2023 12:56:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Order_book](
	[order_id] [int] NULL,
	[book_id] [int] NULL,
	[quantity] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 11/11/2023 12:56:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders](
	[order_id] [int] NOT NULL,
	[user_id] [int] NULL,
	[order_status] [varchar](20) NULL,
	[order_date] [date] NULL,
	[serve_date] [date] NULL,
	[total_price] [decimal](10, 2) NULL,
PRIMARY KEY CLUSTERED 
(
	[order_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Stock]    Script Date: 11/11/2023 12:56:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Stock](
	[book_id] [int] NULL,
	[quantity] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 11/11/2023 12:56:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[user_id] [int] IDENTITY(1,1) NOT NULL,
	[first_name] [varchar](255) NULL,
	[email] [varchar](255) NULL,
	[last_name] [varchar](255) NULL,
	[username] [varchar](50) NULL,
	[password] [varchar](255) NULL,
	[role] [char](25) NULL,
PRIMARY KEY CLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Books] ON 

INSERT [dbo].[Books] ([book_id], [title], [author], [category_name], [price], [description]) VALUES (1, N'The Great Gatsby', N'F. Scott Fitzgerald', N'Fiction', CAST(12.99 AS Decimal(10, 2)), N'A classic novel depicting the American Dream')
INSERT [dbo].[Books] ([book_id], [title], [author], [category_name], [price], [description]) VALUES (2, N'To Kill a Mockingbird', N'Harper Lee', N'Classics', CAST(10.99 AS Decimal(10, 2)), N'A powerful story about racial injustice')
INSERT [dbo].[Books] ([book_id], [title], [author], [category_name], [price], [description]) VALUES (3, N'1984', N'George Orwell', N'Fiction', CAST(9.99 AS Decimal(10, 2)), N'A dystopian novel about totalitarianism and surveillance')
INSERT [dbo].[Books] ([book_id], [title], [author], [category_name], [price], [description]) VALUES (4, N'Pride and Prejudice', N'Jane Austen', N'Science Fiction', CAST(8.99 AS Decimal(10, 2)), N'A timeless romance set in 19th-century England')
INSERT [dbo].[Books] ([book_id], [title], [author], [category_name], [price], [description]) VALUES (5, N'The Catcher in the Rye', N'J.D. Salinger', N'Classics', CAST(11.99 AS Decimal(10, 2)), N'A coming-of-age story of a disaffected teenager')
INSERT [dbo].[Books] ([book_id], [title], [author], [category_name], [price], [description]) VALUES (6, N'To the Lighthouse', N'Virginia Woolf', N'Fiction', CAST(14.99 AS Decimal(10, 2)), N'An experimental novel exploring themes of time and perception')
INSERT [dbo].[Books] ([book_id], [title], [author], [category_name], [price], [description]) VALUES (7, N'Brave New World', N'Aldous Huxley', N'Science Fiction', CAST(10.99 AS Decimal(10, 2)), N'A dystopian vision of a future society')
INSERT [dbo].[Books] ([book_id], [title], [author], [category_name], [price], [description]) VALUES (8, N'Moby-Dick', N'Herman Melville', N'Classics', CAST(13.99 AS Decimal(10, 2)), N'An epic tale of obsession and the search for meaning')
INSERT [dbo].[Books] ([book_id], [title], [author], [category_name], [price], [description]) VALUES (9, N'The Lord of the Rings', N'J.R.R. Tolkien', N'Fiction', CAST(24.99 AS Decimal(10, 2)), N'A fantasy trilogy set in the world of Middle-earth')
INSERT [dbo].[Books] ([book_id], [title], [author], [category_name], [price], [description]) VALUES (10, N'Harry Potter and the Philosopher''s Stone', N'J.K. Rowling', N'Science Fiction', CAST(15.99 AS Decimal(10, 2)), N'The first book in the popular Harry Potter series')
SET IDENTITY_INSERT [dbo].[Books] OFF
GO
INSERT [dbo].[Cart] ([user_id], [book_id], [quantity]) VALUES (1, 1, 12)
GO
INSERT [dbo].[Stock] ([book_id], [quantity]) VALUES (1, 12)
INSERT [dbo].[Stock] ([book_id], [quantity]) VALUES (2, 5)
INSERT [dbo].[Stock] ([book_id], [quantity]) VALUES (3, 3)
INSERT [dbo].[Stock] ([book_id], [quantity]) VALUES (4, 8)
INSERT [dbo].[Stock] ([book_id], [quantity]) VALUES (5, 15)
INSERT [dbo].[Stock] ([book_id], [quantity]) VALUES (6, 2)
INSERT [dbo].[Stock] ([book_id], [quantity]) VALUES (7, 6)
INSERT [dbo].[Stock] ([book_id], [quantity]) VALUES (8, 4)
INSERT [dbo].[Stock] ([book_id], [quantity]) VALUES (9, 12)
INSERT [dbo].[Stock] ([book_id], [quantity]) VALUES (10, 20)
GO
SET IDENTITY_INSERT [dbo].[Users] ON 

INSERT [dbo].[Users] ([user_id], [first_name], [email], [last_name], [username], [password], [role]) VALUES (1, N'Hai', N'hai@gmail.com', N'Nguyen', N'hai', N'12345678', N'user                     ')
INSERT [dbo].[Users] ([user_id], [first_name], [email], [last_name], [username], [password], [role]) VALUES (2, N'Admin', N'admin@gmail.com', N'User', N'admin', N'12345678', N'admin                    ')
INSERT [dbo].[Users] ([user_id], [first_name], [email], [last_name], [username], [password], [role]) VALUES (3, N'Hieu', N'hieu1711@gmail.com', N'Dao', N'hieu', N'12345678', N'user                     ')
SET IDENTITY_INSERT [dbo].[Users] OFF
GO
ALTER TABLE [dbo].[Cart]  WITH CHECK ADD FOREIGN KEY([book_id])
REFERENCES [dbo].[Books] ([book_id])
GO
ALTER TABLE [dbo].[Cart]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [dbo].[Users] ([user_id])
GO
ALTER TABLE [dbo].[Order_book]  WITH CHECK ADD FOREIGN KEY([book_id])
REFERENCES [dbo].[Books] ([book_id])
GO
ALTER TABLE [dbo].[Order_book]  WITH CHECK ADD FOREIGN KEY([order_id])
REFERENCES [dbo].[Orders] ([order_id])
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [dbo].[Users] ([user_id])
GO
ALTER TABLE [dbo].[Stock]  WITH CHECK ADD FOREIGN KEY([book_id])
REFERENCES [dbo].[Books] ([book_id])
GO
USE [master]
GO
ALTER DATABASE [BookStore] SET  READ_WRITE 
GO
