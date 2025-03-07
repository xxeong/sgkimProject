package com.sgkim.todocalendar.todo_calendar_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sgkim.todocalendar.todo_calendar_backend.model.dto.EventDto;
import com.sgkim.todocalendar.todo_calendar_backend.model.entity.EventEntity;
import com.sgkim.todocalendar.todo_calendar_backend.repository.EventRepository;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    public EventService(EventRepository eventRepository,ModelMapper modelMapper){
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
    }

    public List<EventDto> getEvents() {

        return eventRepository.findAll().stream()
                .map(event -> modelMapper.map(event, EventDto.class))
                .collect(Collectors.toList());
    }

    public EventDto getEventById(Long id){
        EventDto event = modelMapper.map(eventRepository.findById(id).orElseThrow(()-> new RuntimeException("id값이 존재 하지 않습니다. id = "+ id)),EventDto.class);

        return event;
    }

    public EventDto addEvent(EventDto event){
        EventEntity entity = modelMapper.map(event, EventEntity.class);
        EventEntity savedEntity = eventRepository.save(entity);
        return modelMapper.map(savedEntity, EventDto.class);
    }

    public EventDto putEvent(Long id, EventDto event) {
        EventEntity eventEntity = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 일정이 없습니다. : " + id));
        eventEntity.setTitle(event.getTitle());
        eventEntity.setDescription(event.getDescription());
        eventEntity.setStartDateTime(event.getStartDateTime());
        eventEntity.setEndDateTime(event.getEndDateTime());
        eventEntity.setLocation(event.getLocation());

        EventEntity updatedEntity = eventRepository.save(eventEntity);
        return modelMapper.map(updatedEntity, EventDto.class);
    }

    //사용을 할지 고민...
    // public EventDto patchEvent(Long id, Map<String, Object> updates) {
    //     EventEntity event = eventRepository.findById(id)
    //             .orElseThrow(() -> new RuntimeException("해당 할일이 없습니다. : " + id));
    //     event.setStatus(updates.get("status").toString());
    //     EventEntity updatedEntity = eventRepository.save(todo);
        
    //     return modelMapper.map(updatedEntity, EventDto.class);
    // }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다: " + id);
        }
        eventRepository.deleteById(id);
    }
}
