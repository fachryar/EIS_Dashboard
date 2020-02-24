<?php

class Pajak_model extends CI_Model{
	public function getTargetPajak($tahun) {
		if ($tahun === null){
			return $this->db->query('SELECT p.id_jenis_pajak, j.jenis_pajak, w.tahun, SUM(p.total) AS "TOTAL_PAJAK", t.jumlah AS "TARGET_TAHUN_INI"
									FROM pajak.pajak_non_pbb p 
									JOIN pajak.waktu w ON p.id_waktu = w.id_waktu
									JOIN pajak.jenis_pajak j ON p.id_jenis_pajak = j.id_jenis_pajak 
									JOIN pajak.target_apbd t ON p.id_jenis_pajak = t.id_jenis_pajak
									GROUP BY p.id_jenis_pajak, w.tahun, j.jenis_pajak, t.jumlah
									ORDER BY w.tahun;')->result_array();
		} else {
			return $this->db->query('SELECT p.id_jenis_pajak, j.jenis_pajak, w.tahun, SUM(p.total) AS "TOTAL_PAJAK", t.jumlah AS "TARGET_TAHUN_INI"
										FROM pajak.pajak_non_pbb p 
										JOIN pajak.waktu w ON p.id_waktu = w.id_waktu
										JOIN pajak.jenis_pajak j ON p.id_jenis_pajak = j.id_jenis_pajak 
										JOIN pajak.target_apbd t ON p.id_jenis_pajak = t.id_jenis_pajak
										WHERE w.tahun = '. $tahun .'
										GROUP BY p.id_jenis_pajak, w.tahun, j.jenis_pajak, t.jumlah
										ORDER BY w.tahun;')->result_array();
		}
	}

	public function getPendapatanAll($tahun) {
		return $this->db->query('SELECT w.bulan, w.tahun, SUM (p.total) AS "TOTAL_PENDAPATAN_PAJAK"
									FROM pajak.pajak_non_pbb p
									JOIN pajak.waktu w ON p.id_waktu = w.id_waktu
									WHERE w.tahun = '. $tahun .'
									GROUP BY w.bulan, w.tahun
									ORDER BY w.tahun;')->result_array();
	}
}