package dev.thiago.cantina.service;

import dev.thiago.cantina.dto.aluno.AlunoRequestDTO;
import dev.thiago.cantina.dto.aluno.AlunoResponseDTO;
import dev.thiago.cantina.entity.Aluno;
import dev.thiago.cantina.exception.AlunoExistenteException;
import dev.thiago.cantina.exception.AlunoNaoEncontradoException;
import dev.thiago.cantina.mapper.AlunoMapper;
import dev.thiago.cantina.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public AlunoResponseDTO salvar(AlunoRequestDTO dto) {
        Optional<Aluno> alunoExistente = alunoRepository.findByNomeIgnoreCaseAndTurma(dto.nome(), dto.turma());

        if (alunoExistente.isPresent()) {
            throw new AlunoExistenteException("Já existe um aluno com o nome '" + dto.nome() + "' na turma selecionada.");
        }
        Aluno aluno = AlunoMapper.toEntity(dto);

        Aluno alunoSalvo = alunoRepository.save(aluno);

        return AlunoMapper.toDTO(alunoSalvo);
    }

    public AlunoResponseDTO buscarPorId(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno com ID '" + id + "' não encontrado."));
        return AlunoMapper.toDTO(aluno);
    }

    public List<AlunoResponseDTO> listar() {
        List<Aluno> alunos = alunoRepository.findAll();
        return alunos.stream().map(AlunoMapper::toDTO).toList();
    }

    public void deletar(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno com ID '" + id + "' não encontrado."));
        alunoRepository.delete(aluno);
    }

    public AlunoResponseDTO atualizar(Long id, AlunoRequestDTO dto) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno com ID '" + id + "' não encontrado."));

        Optional<Aluno> alunoExistente = alunoRepository.findByNomeIgnoreCaseAndTurma(dto.nome(), dto.turma());

        if (alunoExistente.isPresent() && !alunoExistente.get().getId().equals(id)) {
            throw new AlunoExistenteException("Já existe um aluno com o nome '" + dto.nome() + "' na turma selecionada.");

        }

        aluno.setNome(dto.nome());
        aluno.setTurma(dto.turma());

        Aluno alunoAtualizado = alunoRepository.save(aluno);

        return AlunoMapper.toDTO(alunoAtualizado);
    }

}
