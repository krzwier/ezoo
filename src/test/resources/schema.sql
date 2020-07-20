CREATE TABLE IF NOT EXISTS feeding_schedules
(
    `schedule_id` int NOT NULL,
    `feeding_time` varchar(100) NOT NULL,
    `recurrence` varchar(100) NOT NULL,
    `food` varchar(100) NOT NULL,
    `notes` varchar(1000),
    CONSTRAINT feeding_schedules_pkey PRIMARY KEY (schedule_id)
);

CREATE TABLE IF NOT EXISTS animals
(
    `animalid` int NOT NULL,
    `name` varchar(100),
    `taxkingdom` varchar(80),
    `taxphylum` varchar(80),
    `taxclass` varchar(80),
    `taxorder` varchar(80),
    `taxfamily` varchar(80),
    `taxgenus` varchar(80),
    `taxspecies` varchar(80),
    `height` numeric(6,2),
    `weight` numeric(6,2),
    `type` varchar(80),
    `healthstatus` varchar(80),
    `feeding_schedule` int,
    CONSTRAINT animals_pkey PRIMARY KEY (animalid)
);
