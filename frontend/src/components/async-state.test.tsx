import { describe, expect, it, vi } from 'vitest';
import { render, screen } from '@testing-library/react';
import { AsyncState } from './async-state';

describe('AsyncState', () => {
  it('renders the loading state when isLoading is true', () => {
    render(
      <AsyncState isLoading>
        <p>Content</p>
      </AsyncState>,
    );

    expect(screen.getByRole('status')).toHaveTextContent('Loading...');
  });

  it('renders the error state when error is set', () => {
    render(
      <AsyncState isLoading={false} error="Something went wrong">
        <p>Content</p>
      </AsyncState>,
    );

    expect(screen.getByRole('alert')).toHaveTextContent('Something went wrong');
  });

  it('shows a retry button when onRetry is provided alongside an error', async () => {
    const onRetry = vi.fn();
    render(
      <AsyncState isLoading={false} error="Failed" onRetry={onRetry}>
        <p>Content</p>
      </AsyncState>,
    );

    screen.getByRole('button', { name: 'Try again' }).click();
    expect(onRetry).toHaveBeenCalledOnce();
  });

  it('renders the empty state when isEmpty is true', () => {
    render(
      <AsyncState isLoading={false} isEmpty emptyMessage="Nothing here">
        <p>Content</p>
      </AsyncState>,
    );

    expect(screen.getByText('Nothing here')).toBeInTheDocument();
  });

  it('renders children when not loading, not errored, and not empty', () => {
    render(
      <AsyncState isLoading={false}>
        <p>Content</p>
      </AsyncState>,
    );

    expect(screen.getByText('Content')).toBeInTheDocument();
  });
});
