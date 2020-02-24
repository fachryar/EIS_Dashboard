<?php

class RDS_model extends CI_Model{
	public function getAPBD($tahun, $skpd) {
		return $this->db->query('SELECT s.id_skpd, a.tahun, s.nama_skpd, SUM(a.total_anggaran_usulan_pd) AS ANGGARAN_PD, SUM(a.total_anggaran_usulan_kecamatan) AS ANGGARAN_KECAMATAN,
									 SUM(a.total_anggaran_ususlan_dprd) AS ANGGARAN_DPRD, SUM(a.total_apbd_kab) AS TOTAL_ANGGARAN_APBD
									FROM rds.anggaran_skpd a
									JOIN rds.skpd s ON a.id_skpd = s.id_skpd
									WHERE a.tahun = '.$tahun.' AND LOWER(s.nama_skpd) LIKE LOWER('."'%".$skpd."%'".')
									GROUP BY a.tahun, s.nama_skpd, s.id_skpd
									ORDER BY TOTAL_ANGGARAN_APBD DESC;')->result_array();
	}

	public function getSKPD($tahun) {
		return $this->db->query('SELECT s.nama_skpd 
									from rds.anggaran_skpd a 
									join rds.skpd s on a.id_skpd = s.id_skpd 
									WHERE a.tahun = '.$tahun.' 
									order by s.nama_skpd asc;')->result_array();
	}

	public function getProgramSKPD($tahun, $id_skpd) {
		return $this->db->query('SELECT CAST(program AS VARCHAR (5000)) AS PROGRAM, 
									CAST(indikator_program AS VARCHAR (5000)) AS INDIKATOR, 
									CAST(target_program AS VARCHAR (5000)) AS TARGET, dana_program AS DANA
									FROM rds.program
									WHERE id_skpd = '.$id_skpd.' AND tahun = '.$tahun.'
									ORDER BY DANA desc;')->result_array();
	}
}