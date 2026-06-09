-- Art Gallery SCHEMA.SQL

-- Tabela obras (classe abstrata):
create table obras (
	id serial primary key,
	titulo varchar(50) not null,
	autor varchar(100) not null,
	tipo varchar(20) not null,
	ativa boolean not null default true,
	
	constraint CK_tipo check (tipo in ('PINTURA DIGITAL', 'MODELAGEM 3D', 'ARTE GENERATIVA'))
);

-- Tipos de obras (subclasses):

-- Pintura digital
create table pintura_digital (
	id_obra int primary key,
	resolucao varchar(20) not null,
	software_utilizado varchar(50) not null,
	
	constraint FK_pintura_obra foreign key (id_obra) references obras(id) on delete CASCADE
);

-- Modelagem 3D
create table modelagem_3d (
	id_obra int primary key,
	numero_poligonos int not null,
	engine varchar(50) not null,

	constraint FK_modelagem_obra foreign key (id_obra) references obras(id) on delete CASCADE
);

-- Arte Generativa
create table arte_generativa (
	id_obra int primary key,
	algoritmo varchar(50) not null,
	seed bigint not null,
	
	constraint FK_generativa_obra foreign key (id_obra) references obras(id) on delete CASCADE
);

-- Tabela avaliacoes (Relacionamento 1:N com obras)
create table avaliacoes (
	id int primary key,
	id_obra int not null, 
	usuario varchar(100) not null,
	nota int not null,
	comentario TEXT not null,
	
	constraint FK_avaliacao_obra foreign key (id_obra) references obras(id) on delete CASCADE,
	constraint CK_nota check (nota between 0 and 10)
);

-- Tabela exposicoes
create table exposicoes (
	id serial primary key,
	nome varchar(50) not null
)

-- Tabela intermediária (Relacionamento N:N entre obras e exposicoes)
create table exposicao_obra (
	id_obra int not null,
	id_exposicao int not null,
	
	primary key (id_obra, id_exposicao),
	constraint fk_eo_obra foreign key (id_obra) references obras(id) on delete cascade,
	constraint fk_eo_exposicao foreign key (id_exposicao) references exposicoes(id) on delete cascade
)