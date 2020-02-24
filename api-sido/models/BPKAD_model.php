<?php
	
	class BPKAD_model extends CI_MODEL {

		public function getTotalAPBD($tahun){
			return $this->db->query('SELECT r.u1, t.tahun, SUM(b.jml_anggaran) AS ANGGARAN, SUM(b.jml_realisasi) AS REALISASI, 
										CONCAT(CAST(SUM(b.jml_realisasi) / SUM(b.jml_anggaran) * 100 AS DECIMAl (5,2)), '."'%'".') AS PERCENTAGE
										FROM bpkad.bpkad b
										JOIN bpkad.bpkad_rekening r ON b.id_bpkad_rekening = r.id_bpkad_rekening
										JOIN bpkad.tahun_bpkad t ON b.id_tahun_bpkad = t.id_tahun_bpkad
										WHERE t.tahun = '.$tahun.'
										GROUP BY r.u1, t.tahun 
										ORDER BY r.u1 DESC;')->result_array();
												}

		public function getAnggaranRealisasi1($kategori, $tahun){
			return $this->db->query('SELECT r.u2 AS PENDAPATAN_DAERAH, SUM(b.jml_anggaran) AS TOTAL_TARGET, SUM(b.jml_realisasi) AS TOTAL_REALISASI,
										CONCAT(CAST(SUM(b.jml_realisasi) / SUM(b.jml_anggaran) * 100 AS DECIMAl (5,2)), '."'%'".') AS PERCENTAGE
										FROM bpkad.bpkad b
										JOIN bpkad.bpkad_rekening r ON b.id_bpkad_rekening = r.id_bpkad_rekening
										JOIN bpkad.tahun_bpkad t ON b.id_tahun_bpkad = t.id_tahun_bpkad
										WHERE r.u1 LIKE '."'".$kategori."%'".' AND t.tahun = '.$tahun.'
										GROUP BY r.u2
										ORDER BY r.u2 ASC;')->result_array();
		}

		public function getAnggaranRealisasi2($kategori, $tahun){
			return $this->db->query('SELECT r.u2, r.u3, SUM(b.jml_anggaran) AS TOTAL_ANGGARAN, SUM(b.jml_realisasi) AS TOTAL_REALISASI,
										CONCAT(CAST(SUM(b.jml_realisasi) / SUM(b.jml_anggaran) * 100 AS DECIMAl (5,2)), '."'%'".') AS PERCENTAGE
										FROM bpkad.bpkad b
										JOIN bpkad.bpkad_rekening r ON b.id_bpkad_rekening = r.id_bpkad_rekening
										JOIN bpkad.tahun_bpkad t ON b.id_tahun_bpkad = t.id_tahun_bpkad
										WHERE r.u1 LIKE '."'".$kategori."%'".' AND t.tahun = '.$tahun.'
										GROUP BY r.u2, r.u3
										ORDER BY r.u2 ASC;')->result_array();
		}
	}

