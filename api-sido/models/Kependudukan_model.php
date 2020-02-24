<?php

class Kependudukan_model extends CI_Model{
	public function getJumlahPenduduk(){
		return $this->db->query('SELECT (SUM(PRIA) + SUM(WANITA)) AS TOTAL_PENDUDUK
   									FROM kependudukan.kependudukan;')->result_array();
	}

	public function getByGender(){
		return $this->db->query('SELECT SUM(PRIA) AS JUMLAH_PRIA, SUM(WANITA) AS JUMLAH_WANITA
   									FROM kependudukan.kependudukan;')->result_array();
	}

	public function getByGenderDetail(){
		return $this->db->query('SELECT kecamatan, SUM(PRIA) AS JUMLAH_PRIA, SUM(WANITA) AS JUMLAH_WANITA, (SUM(PRIA) + SUM(WANITA)) AS TOTAL_PENDUDUK
									FROM kependudukan.kependudukan
									GROUP BY kecamatan;')->result_array();
	}

	public function getByPendidikan(){
		return $this->db->query('SELECT SUM(TBS) AS TBS, SUM(BTS) AS BTS, SUM(TSS) AS TSS, SUM(SLTP) AS SLTP, SUM(SLTA) AS SLTA, SUM(D1) AS D1_D2, SUM(D2) AS D3, SUM(S1) AS D4_S1, SUM(S2) AS S2, SUM(S3) AS S3
			FROM kependudukan.kependudukan;')->result_array();
	}

	public function getByPendidikanDetail(){
		return $this->db->query('SELECT kecamatan, SUM(TBS) AS TBS, SUM(BTS) AS BTS, SUM(TSS) AS TSS, SUM(SLTP) AS SLTP, SUM(SLTA) AS SLTA, SUM(D1) AS D1_D2, SUM(D2) AS D3, SUM(S1) AS D4_S1, SUM(S2) AS S2, SUM(S3) AS S3
			FROM kependudukan.kependudukan
			GROUP BY kecamatan;')->result_array();
	}

	public function getByPekerjaan(){
		return $this->db->query('SELECT pekerjaan, jumlah FROM kependudukan.pekerjaan WHERE jumlah NOT LIKE 0 ORDER BY jumlah desc;')->result_array();
	}

	public function getByAgama(){
		return $this->db->query('SELECT a.agama, SUM(f.JUMLAH) AS JUMLAH
									FROM kependudukan.fakta_agama f
									JOIN kependudukan.agama a ON f.id_agama = a.id
									GROUP BY a.agama
									ORDER BY JUMLAH DESC;')->result_array();
	}

	public function getByAgamaDetail($kecamatan){
		return $this->db->query('SELECT f.nama_kecamatan, a.agama, SUM(f.JUMLAH) AS JUMLAH
									FROM kependudukan.fakta_agama f
									JOIN kependudukan.agama a ON f.id_agama = a.id
									WHERE LOWER (f.nama_kecamatan) LIKE LOWER ('."'".$kecamatan."'".')
									GROUP BY f.nama_kecamatan, a.agama 
									ORDER BY JUMLAH DESC;')->result_array();
	}

	public function getKecamatan(){
			return $this->db->query('SELECT nama_kecamatan FROM kependudukan.fakta_agama GROUP BY nama_kecamatan;')->result_array();
	}

	public function getByUsia(){
			return $this->db->query('SELECT u.usia, (SUM(f.JUMLAH_PRIA) + SUM(f.JUMLAH_WANITA)) AS JUMLAH
										FROM kependudukan.usia u
										JOIN kependudukan.fakta_usia f ON u.id = f.id_usia
										GROUP BY u.usia;')->result_array();
	}




}