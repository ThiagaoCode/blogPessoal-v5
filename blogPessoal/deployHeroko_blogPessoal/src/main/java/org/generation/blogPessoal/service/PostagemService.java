package org.generation.blogPessoal.service;

import java.util.Optional;

import org.generation.blogPessoal.model.Postagem;
import org.generation.blogPessoal.model.Tema;
import org.generation.blogPessoal.repository.PostagemRepository;
import org.generation.blogPessoal.repository.TemaRepository;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class PostagemService {
	
	@Autowired
	private  PostagemRepository repositorioP;
	
	@Autowired
	private UsuarioRepository repositorioU;
	
	@Autowired
	private TemaRepository repositorioT;
	
	public Optional<?> cadastrarPostagem(Postagem novaPostagem) {
		Optional<Tema> objetoExistente = repositorioT.findById(novaPostagem.getTema().getId());
		return repositorioU.findById(novaPostagem.getUsuario().getId()).map(usuarioExistente -> {
			if (objetoExistente.isPresent()) {
				novaPostagem.setUsuario(usuarioExistente);
				novaPostagem.setTema(objetoExistente.get());
				return Optional.ofNullable(repositorioP.save(novaPostagem));
			} else {
				return Optional.empty();
			}
		}).orElseGet(() -> {
			return Optional.empty();
		});
	}
	
	public Optional<Postagem> alterarPostagem(Postagem postagemParaAlterar) {
		return repositorioP.findById(postagemParaAlterar.getId()).map(postagemExistente -> {
			postagemExistente.setTitulo(postagemParaAlterar.getTitulo());
			postagemExistente.setTexto(postagemParaAlterar.getTexto());
			return Optional.ofNullable(repositorioP.save(postagemExistente));
		}).orElseGet(() -> {
			return Optional.empty();
		});
	}

}
