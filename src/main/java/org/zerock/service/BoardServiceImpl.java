package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardAttachMapper;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor//생성자 만들어줌 setter(onmethod_ = @autowired) 대신사용가능
public class BoardServiceImpl implements BoardService {

//	@Setter(onMethod_ = @Autowired) 스프링 버전 4.3(?)부터 안써도됨 대신 위의 생성자 만들어줘야됨
	private BoardMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;
	
	@Transactional
	@Override
	public void register(BoardVO board) {
		// TODO Auto-generated method stub
		
		log.info("register............" +board);
		
		mapper.insertSelectKey(board);
		
		if(board.getAttachList() == null || board.getAttachList().size()<=0) {
			return;
		}
		
		board.getAttachList().forEach(attach -> {
			attach.setBno(board.getBno());

			attachMapper.insert(attach);
		});

	}

	@Override
	public BoardVO get(Long bno) {
		// TODO Auto-generated method stub
		log.info("bno..............." +bno);
		
		return mapper.read(bno);
	}

	@Override
	@Transactional
	public boolean modify(BoardVO board) {
		// TODO Auto-generated method stub
		
		log.info("modify....."+ board);
		
		attachMapper.deleteAll(board.getBno());
		
		boolean modifyResult = mapper.update(board) == 1;
		
		if(board.getAttachList() != null) {
		if(modifyResult && board.getAttachList().size() > 0) {
			board.getAttachList().forEach(attach -> {
				attach.setBno(board.getBno());
				attachMapper.insert(attach);
				
				});
			}
		}
		return modifyResult;
	}

	@Transactional
	@Override
	public boolean remove(Long bno) {
		// TODO Auto-generated method stub
		
		log.info("remove...." +bno);
		
		attachMapper.deleteAll(bno);
		return mapper.delete(bno) == 1;
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		// TODO Auto-generated method stub
		log.info("get List with criteria"+cri);
		return mapper.getListWithPaging(cri);
	}
	
	@Override
	public int getTotal(Criteria cri) {
		
		log.info("getTotal count");
		return mapper.getTotalCount(cri);
	}
	
	@Override
	public List<BoardAttachVO> getAttachList(Long bno){
		
		log.info("get attach list by bno: " +bno);
		return attachMapper.findByBno(bno);
	}

}
