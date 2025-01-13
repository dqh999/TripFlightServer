PARTITION BY RANGE COLUMNS(departure_airport, arrival_airport) (
    PARTITION p_f_han_sgn VALUES IN (('HAN', 'SGN')),
    PARTITION p_f_sgn_han VALUES IN (('SGN', 'HAN')),
);