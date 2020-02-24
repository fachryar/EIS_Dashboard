<?php

class BKD_model extends CI_Model{
	public function searchPegawai($nama, $golongan, $skpd, $nip){
		return $this->db->query('SELECT p.nip, p.nama, p.jenis_kelamin, k.kedudukan, s.status_pegawai, g.golongan, g.pangkat, u.unit_kerja, p.usia
									FROM bkd.pegawai p
									JOIN bkd.kedudukan k ON p.id_kedudukan = k.id_kedudukan
									JOIN bkd.status_pegawai s ON p.id_status_pegawai = s.id_status_pegawai
									JOIN bkd.pangkat_golongan g ON p.id_pangkat_golongan = g.id_pangkat_golongan
									JOIN bkd.unit_kerja u ON p.id_unit_kerja = u.id_unit_kerja
									WHERE LOWER (p.nama) LIKE LOWER(' . "'%" .$nama. "%'".')
										AND LOWER (g.golongan) LIKE LOWER (' . "'" .$golongan. "%'".')
										AND LOWER (u.unit_kerja) LIKE LOWER (' . "'%" .$skpd. "%'".')
										AND LOWER (p.nip) LIKE LOWER(' . "'" .$nip. "%'".')
									ORDER BY g.golongan DESC LIMIT 10;')->result_array();
	}

	public function getAllSkpd(){
		return $this->db->query('SELECT unit_kerja FROM bkd.unit_kerja ORDER BY unit_kerja ASC;')->result_array();
	}

	public function countPegawaiByKel(){
		return $this->db->query('SELECT jenis_kelamin, COUNT(*) AS JUMLAH
									FROM bkd.pegawai
									GROUP BY jenis_kelamin
									ORDER BY jenis_kelamin;')->result_array();
	}

	public function countPegawaiByUsia(){
		return $this->db->query('SELECT r.rentang_umur, COUNT(*) AS JUMLAH
									FROM bkd.rentang_umur r
									JOIN bkd.pegawai p ON r.id_rentang_umur = p.id_rentang_umur
									GROUP BY r.rentang_umur
									ORDER BY jumlah DESC;')->result_array();
	}

	public function countPegawaiBySKPD(){
		return $this->db->query('SELECT r.rentang_umur, COUNT(*) AS JUMLAH
									FROM bkd.rentang_umur r
									JOIN bkd.pegawai p ON r.id_rentang_umur = p.id_rentang_umur
									GROUP BY r.rentang_umur
									ORDER BY jumlah DESC;')->result_array();
	}

	public function countPegawaiByGolKel($gol){
		return $this->db->query('SELECT g.golongan, g.pangkat,  
									COUNT(CASE WHEN jenis_kelamin= ' . "'Pria'" . ' THEN 1 ELSE NULL END) AS JUMLAH_PRIA,
									COUNT(CASE WHEN jenis_kelamin= ' . "'Wanita'". ' THEN 1 ELSE NULL END) AS JUMLAH_Wanita
									FROM bkd.pangkat_golongan g
									JOIN bkd.pegawai p ON g.id_pangkat_golongan = p.id_pangkat_golongan
									WHERE LOWER (g.golongan) LIKE LOWER ('."'".$gol."/%'".')
									GROUP BY g.golongan, g.pangkat
									ORDER BY g.golongan ASC;')->result_array();
	}
}