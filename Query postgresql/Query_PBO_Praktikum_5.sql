--Drop dulu tabel lama, kita pakai tabel baru
DROP TABLE penjualan_perangkat_lunak
DROP TABLE login
DROP TABLE perangkat_elektronik CASCADE
DROP TABLE detail_perangkat CASCADE

-- Tabel login
CREATE TABLE login (
	username VARCHAR(50) PRIMARY KEY,
	passwordnya VARCHAR(100)
);

-- Tabel perangkat_elektronik
CREATE TABLE perangkat_elektronik (
    nomor_seri VARCHAR(50) PRIMARY KEY,
    jenis_perangkat VARCHAR(100) NOT NULL,
    merek_perangkat VARCHAR(100) NOT NULL,
    nama_perangkat VARCHAR(100) NOT NULL,
    model_perangkat VARCHAR(100)
);

-- Tabel detail_perangkat
CREATE TABLE detail_perangkat (
    id_detail SERIAL PRIMARY KEY,
    nomor_seri VARCHAR(50) NOT NULL,
    warna VARCHAR(50) NOT NULL,
    harga BIGINT NOT NULL,
    stok INTEGER NOT NULL,
    FOREIGN KEY (nomor_seri) 
        REFERENCES perangkat_elektronik(nomor_seri)ON DELETE CASCADE ON UPDATE CASCADE
);

Select * from perangkat_elektronik

INSERT INTO login (username, passwordnya) VALUES
('verroy01', 'passA7x!'),
('user_alpha', 'alpha12345'),
('rinzoku', 'Rk#2025!'),
('techmaster', 'tm_secure88'),
('nova_user', 'Nv!2024pass'),
('skyline', 'skyPwd77'),
('aurora_dev', 'Aur0ra!dev'),
('bytehunter', 'Byte#512'),
('moonlight', 'Ml!shadow22'),
('zenith_admin', 'ZAdmin#909');

Select * From login
